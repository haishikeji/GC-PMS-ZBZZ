<template>
  <div class="app-container">
    <div>
      <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px">

        <el-form-item label="部门" prop="units">
          <el-select v-model="units" placeholder="请选择部门" clearable multiple size="small">
            <el-option
              v-for="dict in dict.type.staff_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="班组" prop="teams">
          <el-select v-model="teams" placeholder="请选择班组" clearable multiple size="small">
            <el-option
              v-for="dict in dict.type.team_divide"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="岗位" label-width="200" prop="actualposts">
          <el-select v-model="actualposts" placeholder="请选择岗位" clearable multiple size="small">
            <el-option
                v-for="dict in dict.type.actualpost"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery"> 搜索 </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"> 重置 </el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getChartData"></right-toolbar>
        <div class="fr imgfr">
          <el-tooltip content="导出图片" effect="dark" placement="top">
            <el-button circle icon="el-icon-picture-outline-round" size="mini" @click="exportImg"/>
          </el-tooltip>
        </div>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <div class="bz-box">
            <i class="iconfont icon-star"></i>安全代表
            <i class="iconfont icon-round"></i>四级消防员
            <i class="iconfont icon-yuanjiaoliujiaoxing"></i>SCBA使用人员
            <i class="iconfont icon-plus"></i>急救员
          </div>
        </el-col>
      </el-row>
      <div id="tree" ref="orgChartDom"></div>
    </div>
    <vue-draggable-resizable :draggable="dragMove" h="auto" style="background-color:white" w="auto">
      <div id="branch" ref="branch" class="zoom" @wheel.prevent="handleTableWheel($event)">
        <branch :dataArray="list1"></branch>
      </div>
    </vue-draggable-resizable>
    <!-- 侧栏 --->
    <el-drawer
        ref="drawer"
        :visible.sync="drawer"
        direction="rtl"
        title="详情">
      <div style="margin: 0px 10px">
        <el-descriptions :column="1" border size="normal">
          <el-descriptions-item label="部门">{{unitFormat(staffData.unit)}}</el-descriptions-item>
          <el-descriptions-item label="员工编号">{{ staffData.staffid }}</el-descriptions-item>
          <el-descriptions-item label="员工姓名">{{ staffData.name }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{sexFormat(staffData.sex)}}</el-descriptions-item>
          <el-descriptions-item v-hasPermi="['plant:staffmgr:detail']" label="身份证号">{{ staffData.idCard }}
          </el-descriptions-item>
          <el-descriptions-item label="联系方式">{{ staffData.contact }}</el-descriptions-item>
          <el-descriptions-item label="岗位">{{postFormat(staffData.actualpost)}}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{ staffData.email }}</el-descriptions-item>
          <el-descriptions-item label="班组">{{teamFormat(staffData.team)}}</el-descriptions-item>
          <el-descriptions-item v-hasPermi="['plant:staffmgr:detail']" label="住址">{{ staffData.address }}
          </el-descriptions-item>
          <el-descriptions-item label="学历">{{eduFormat(staffData.education)}}</el-descriptions-item>
          <el-descriptions-item v-hasPermi="['plant:staffmgr:detail']" label="专业">{{ staffData.speciality }}
          </el-descriptions-item>
          <el-descriptions-item label="特殊职能">{{dutyFormat(staffData.specialDuty)}}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import VueDraggableResizable from 'vue-draggable-resizable'
import Branch from './branch'
import {getStaffmgr, listOgzStaffmgr} from "@/api/plant/staffmgr";
import {getToken} from "@/utils/auth";
import html2canvas from 'html2canvas'

export default {
  dicts:['actualpost','staff_unit','team_divide','sys_user_sex','education','special_duty'],
  components: {Branch, VueDraggableResizable},
  data() {
    return {
      showSearch: false,
      // 实际岗位字典
      actualpostOptions: [],
      showOrgChartData: [],
      staffmgrList: [],
      // 部门字典
      unitOptions: [],
      // 班值字典
      teamOptions: [],
      // 查询参数
      queryParams: {
        unit: null,
        team: null,
        actualpost: null,
        education: null,
        enAbility: null,
        educations: null,
        units: null,
        teams: null,
        actualposts: null,
      },
      units: [],
      teams: [],
      actualposts: ["8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40", "42", "44", "46"],
      dragMove: true,
      drawer: false,
      info: {},
      //{"id":10109,"pId":0,"label":"朱晶","post":"装置经理","secretary":[[{"id":10113,"pId":10109,"label":"赵建清","post":"装置副经理","secretary":[[],[]],"children":[{"id":10101,"pId":10113,"label":"张磊","post":"资深工程师","secretary":[[],[]]},{"id":10103,"pId":10113,"label":"高勇","post":"资深工程师","secretary":[[],[]]}]},{"id":10117,"pId":10109,"label":"居海波","post":"装置副经理","secretary":[[],[]],"children":[{"id":10106,"pId":10117,"label":"邹生耀","post":"资深工程师","secretary":[[],[]]}]},{"id":10102,"pId":10109,"label":"韦岳平","post":"装置副经理","secretary":[[],[]]}],[]],"children":[{"id":10116,"pId":10109,"label":"陈琨","post":"资深工程师","secretary":[[],[]]},{"id":10114,"pId":10109,"label":"樊宏斌","post":"生产主管","secretary":[[],[]]},{"id":10108,"pId":10109,"label":"袁永成","post":"生产主管","secretary":[[],[]]},{"id":10107,"pId":10109,"label":"潘传安","post":"生产主管","secretary":[[],[]]},{"id":10104,"pId":10109,"label":"胡文逵","post":"生产主管","secretary":[[],[]]},{"id":10110,"pId":10109,"label":"孙文龙","post":"生产主管","secretary":[[],[]]},{"id":10115,"pId":10109,"label":"鲍波","post":"工长","secretary":[[],[]]},{"id":10043,"pId":10109,"label":"占丽萍","post":"职员","secretary":[[],[]]},{"id":10112,"pId":10109,"label":"何氢","post":"主操（白班）","secretary":[[],[]]},{"id":10054,"pId":10109,"label":"李滕","post":"主操（白班）","secretary":[[],[]]},{"id":10001,"pId":10109,"label":"徐建飞","post":"倒班班长","secretary":[[],[]],"children":[{"id":10014,"pId":10001,"label":"陈涛","post":"倒班副班长","secretary":[[],[]]},{"id":10012,"pId":10001,"label":"陆危圣","post":"倒班副班长","secretary":[[],[]]},{"id":10008,"pId":10001,"label":"任秋香","post":"主操","secretary":[[],[]]},{"id":10019,"pId":10001,"label":"刘伟程","post":"主操","secretary":[[],[]]},{"id":10010,"pId":10001,"label":"张磊","post":"主操","secretary":[[],[]]},{"id":10015,"pId":10001,"label":"崔海军","post":"主操","secretary":[[],[]]},{"id":10000,"pId":10001,"label":"王俊","post":"主操","secretary":[[],[]]},{"id":10003,"pId":10001,"label":"马国春","post":"主操","secretary":[[],[]]},{"id":10004,"pId":10001,"label":"申海宁","post":"主操","secretary":[[],[]]},{"id":10006,"pId":10001,"label":"张燕燕","post":"主操","secretary":[[],[]]},{"id":10009,"pId":10001,"label":"缪海荣","post":"主操","secretary":[[],[]]},{"id":10017,"pId":10001,"label":"高峰","post":"主操","secretary":[[],[]]},{"id":10018,"pId":10001,"label":"蒋恒兵","post":"主操","secretary":[[],[]]},{"id":10020,"pId":10001,"label":"刘金京","post":"主操","secretary":[[],[]]},{"id":10002,"pId":10001,"label":"张力飞","post":"主操","secretary":[[],[]]},{"id":10005,"pId":10001,"label":"李欣阳","post":"主操","secretary":[[],[]]},{"id":10007,"pId":10001,"label":"宋月民","post":"主操","secretary":[[],[]]},{"id":10013,"pId":10001,"label":"贾云飞","post":"主操","secretary":[[],[]]},{"id":10016,"pId":10001,"label":"张炜","post":"主操","secretary":[[],[]]},{"id":10011,"pId":10001,"label":"宋章浩","post":"主操","secretary":[[],[]]}]},{"id":10031,"pId":10109,"label":"臧闽军","post":"倒班班长","secretary":[[],[]],"children":[{"id":10028,"pId":10031,"label":"朱健","post":"倒班副班长","secretary":[[],[]]},{"id":10032,"pId":10031,"label":"袁晨","post":"倒班副班长","secretary":[[],[]]},{"id":10035,"pId":10031,"label":"陈海飞","post":"主操","secretary":[[],[]]},{"id":10024,"pId":10031,"label":"刘尊超","post":"主操","secretary":[[],[]]},{"id":10026,"pId":10031,"label":"马卫兵","post":"主操","secretary":[[],[]]},{"id":10027,"pId":10031,"label":"曹西元","post":"主操","secretary":[[],[]]},{"id":10029,"pId":10031,"label":"顾仁海","post":"主操","secretary":[[],[]]},{"id":10036,"pId":10031,"label":"李静","post":"主操","secretary":[[],[]]},{"id":10037,"pId":10031,"label":"朱永宜","post":"主操","secretary":[[],[]]},{"id":10039,"pId":10031,"label":"封公瑾","post":"主操","secretary":[[],[]]},{"id":10040,"pId":10031,"label":"姜翠君","post":"主操","secretary":[[],[]]},{"id":10022,"pId":10031,"label":"周湘","post":"主操","secretary":[[],[]]},{"id":10041,"pId":10031,"label":"梁宏伟","post":"主操","secretary":[[],[]]},{"id":10025,"pId":10031,"label":"蒋宁宁","post":"主操","secretary":[[],[]]},{"id":10030,"pId":10031,"label":"吴中鑫","post":"主操","secretary":[[],[]]},{"id":10038,"pId":10031,"label":"刘辉","post":"主操","secretary":[[],[]]},{"id":10033,"pId":10031,"label":"郑骅","post":"主操","secretary":[[],[]]},{"id":10034,"pId":10031,"label":"张琦","post":"主操","secretary":[[],[]]},{"id":10023,"pId":10031,"label":"邓阳","post":"主操","secretary":[[],[]]},{"id":10021,"pId":10031,"label":"李凤希","post":"主操","secretary":[[],[]]}]},{"id":10049,"pId":10109,"label":"沈涛","post":"倒班班长","secretary":[[],[]],"children":[{"id":10048,"pId":10049,"label":"乔刚","post":"倒班副班长","secretary":[[],[]]},{"id":10045,"pId":10049,"label":"宦文恺","post":"倒班副班长","secretary":[[],[]]},{"id":10050,"pId":10049,"label":"卓兴凤","post":"主操","secretary":[[],[]]},{"id":10052,"pId":10049,"label":"阚久龙","post":"主操","secretary":[[],[]]},{"id":10055,"pId":10049,"label":"王超","post":"主操","secretary":[[],[]]},{"id":10056,"pId":10049,"label":"张永","post":"主操","secretary":[[],[]]},{"id":10058,"pId":10049,"label":"宋浦江","post":"主操","secretary":[[],[]]},{"id":10060,"pId":10049,"label":"夏军","post":"主操","secretary":[[],[]]},{"id":10047,"pId":10049,"label":"劳青川","post":"主操","secretary":[[],[]]},{"id":10062,"pId":10049,"label":"邢启元","post":"主操","secretary":[[],[]]},{"id":10061,"pId":10049,"label":"韩叶锋","post":"主操","secretary":[[],[]]},{"id":10053,"pId":10049,"label":"谢振华","post":"主操","secretary":[[],[]]},{"id":10057,"pId":10049,"label":"黄梓赫","post":"主操","secretary":[[],[]]},{"id":10059,"pId":10049,"label":"周权","post":"主操","secretary":[[],[]]},{"id":10046,"pId":10049,"label":"赵振湘","post":"主操","secretary":[[],[]]},{"id":10051,"pId":10049,"label":"沈文勇","post":"主操","secretary":[[],[]]},{"id":10042,"pId":10049,"label":"卞传庆","post":"主操","secretary":[[],[]]},{"id":10044,"pId":10049,"label":"李士军","post":"主操","secretary":[[],[]]}]},{"id":10064,"pId":10109,"label":"刘遵胜","post":"倒班班长","secretary":[[],[]],"children":[{"id":10063,"pId":10064,"label":"王维庆","post":"倒班副班长","secretary":[[],[]]},{"id":10083,"pId":10064,"label":"郝维祥","post":"倒班副班长","secretary":[[],[]]},{"id":10073,"pId":10064,"label":"李飞虎","post":"主操","secretary":[[],[]]},{"id":10075,"pId":10064,"label":"杨赞","post":"主操","secretary":[[],[]]},{"id":10078,"pId":10064,"label":"何叡颖","post":"主操","secretary":[[],[]]},{"id":10082,"pId":10064,"label":"王欢","post":"主操","secretary":[[],[]]},{"id":10084,"pId":10064,"label":"王守清","post":"主操","secretary":[[],[]]},{"id":10079,"pId":10064,"label":"王竹宣","post":"主操","secretary":[[],[]]},{"id":10066,"pId":10064,"label":"徐乐","post":"主操","secretary":[[],[]]},{"id":10071,"pId":10064,"label":"胡远斌","post":"主操","secretary":[[],[]]},{"id":10074,"pId":10064,"label":"马云凤","post":"主操","secretary":[[],[]]},{"id":10077,"pId":10064,"label":"秦飞","post":"主操","secretary":[[],[]]},{"id":10081,"pId":10064,"label":"程佩佩","post":"主操","secretary":[[],[]]},{"id":10069,"pId":10064,"label":"孙夕斌","post":"主操","secretary":[[],[]]},{"id":10070,"pId":10064,"label":"胡保宁","post":"主操","secretary":[[],[]]},{"id":10068,"pId":10064,"label":"王刚","post":"主操","secretary":[[],[]]},{"id":10072,"pId":10064,"label":"王勇","post":"主操","secretary":[[],[]]},{"id":10065,"pId":10064,"label":"陈熹","post":"主操","secretary":[[],[]]},{"id":10067,"pId":10064,"label":"朱国柱","post":"主操","secretary":[[],[]]},{"id":10080,"pId":10064,"label":"孙浩","post":"主操","secretary":[[],[]]}]}]}
      list1: [],
      list2: [],
      // 报告附件参数
      doc: {
        file: "",
        // 是否显示弹出层（报告附件）
        open: false,
        // 弹出层标题（报告附件）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 报告附件上传位置编号
        ids: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/commonfile/uploadFile",
        commonfileList: null,
        queryParams: {
          pId: null,
          pType: 'staffmgr'
        },
        pType: 'staffmgr',
        pId: null
      },
      pdf: {
        title: '',
        pdfUrl: '',
        numPages: null,
        open: false,
        pageNum: 1,
        pageTotalNum: 1,
        loadedRatio: 0,
      },
      staffData: {},
      sexOptions: [],
      educationOptions: [],
      specialDutyOptions: [],
    }
  },
  created() {
    this.getChartData()
  },
  mounted() {
    this.bus.$on('info', (data) => {
      this.staffData = {}
      console.log(data)
      this.drawer = true
      getStaffmgr(data.id).then(response => {
        this.staffData = response.data;
      });
    })
  },
  methods: {
    postFormat(post) {
      return this.selectDictLabel(this.dict.type.actualpost, post)
    },
    sexFormat(val) {
      return this.selectDictLabel(this.dict.type.sys_user_sex, val)
    },
    unitFormat(val) {
      return this.selectDictLabel(this.dict.type.staff_unit, val)
    },
    teamFormat(val) {
      return this.selectDictLabel(this.dict.type.team_divide, val)
    },
    eduFormat(val) {
      return this.selectDictLabel(this.dict.type.education, val)
    },
    dutyFormat(val) {
      return this.selectDictLabels(this.dict.type.special_duty, val)
    },
    handleTableWheel(event) {
      let obj = this.$refs['branch']
      return this.tableZoom(obj, event)
    },
    tableZoom(obj, event) {
      // 一开始默认是100%
      let zoom = parseInt(obj.style.zoom, 10) || 100
      // 滚轮滚一下wheelDelta的值增加或减少120
      zoom += event.wheelDelta / 12
      if (zoom > 50) {
        obj.style.zoom = zoom + '%'
      }
      return false
    },
    dclick() {
      this.drawer = true
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.units = []
      this.teams = []
      this.actualposts = []
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getChartData();
    },
    getChartData() {
      this.queryParams.units = this.units.join()
      this.queryParams.teams = this.teams.join()
      this.queryParams.actualposts = this.actualposts.join()
      listOgzStaffmgr(this.queryParams).then(response => {
        this.staffmgrList = response;
        this.list2 = []

        for (let i = 0; i < this.staffmgrList.length; i++) {
          let post = this.selectDictLabel(this.dict.type.actualpost, this.staffmgrList[i].actualpost)
          if (this.staffmgrList[i].pId == 0) {
            let nodeData = {
              id: this.staffmgrList[i].id,
              pId: this.staffmgrList[i].pId,
              label: this.staffmgrList[i].name,
              post: post,
              secretary: [[], []],
              // img: 'http://47.114.101.16:8080' + process.env.VUE_APP_BASE_API +　this.staffmgrList[i].photo,
              img: process.env.VUE_APP_BASE_API + this.staffmgrList[i].photo,
              bz1: false,
              bz2: false,
              bz3: false,
              bz4: false,
            }
            if (this.staffmgrList[i].specialDuty) {
              // console.log(this.staffmgrList[i].specialDuty)
              let dutyArr = this.staffmgrList[i].specialDuty.split(",")
              for (let i = 0; i < dutyArr.length; i++) {
                if (dutyArr[i] == "10") {
                  nodeData.bz1 = true
                } else if (dutyArr[i] == "12") {
                  nodeData.bz2 = true
                } else if (dutyArr[i] == "14") {
                  nodeData.bz3 = true
                } else if (dutyArr[i] == "16") {
                  nodeData.bz4 = true
                }
              }
            }
            this.list2.push(nodeData)
          } else {
            let nodeData = {
              id: this.staffmgrList[i].id,
              pId: this.staffmgrList[i].pId,
              label: this.staffmgrList[i].name,
              actualpost: this.staffmgrList[i].actualpost,
              education: this.staffmgrList[i].education,
              post: post,
              img: process.env.VUE_APP_BASE_API + this.staffmgrList[i].photo,
              // img: 'http://47.114.101.16:8080' + process.env.VUE_APP_BASE_API +　this.staffmgrList[i].photo,
              bz1: false,
              bz2: false,
              bz3: false,
              bz4: false
            }
            if (this.staffmgrList[i].specialDuty) {
              // console.log(this.staffmgrList[i].specialDuty)
              let dutyArr = this.staffmgrList[i].specialDuty.split(",")
              for (let i = 0; i < dutyArr.length; i++) {
                if (dutyArr[i] == "10") {
                  nodeData.bz1 = true
                } else if (dutyArr[i] == "12") {
                  nodeData.bz2 = true
                } else if (dutyArr[i] == "14") {
                  nodeData.bz3 = true
                } else if (dutyArr[i] == "16") {
                  nodeData.bz4 = true
                }
              }
            }
            this.list2.push(nodeData)
          }
        }

        this.list1 = this.listToTree(this.list2)
        // console.log(JSON.stringify(this.list1))
      })
    },
    listToTree(list) {
      let map = {};
      list.forEach(item => {
        if (!map[item.id]) {
          map[item.id] = item;
        }
      });
      list.forEach(item => {
        if (item.pId !== 0) {
          if (item.post === '生产助理') {
            map[item.pId].secretary ? map[item.pId].secretary[0].push(item) : map[item.pId].secretary[0] = [item];
          } else if (item.post === '实习生' || item.post === '管培生') {

          } else {
            map[item.pId].children ? map[item.pId].children.push(item) : map[item.pId].children = [item];
          }
        }
      });
      return list.filter(item => {
        if (item.pId === 0) {
          return item;
        }
      })
    },
    // 文件下载处理
    handleDownload(row) {
      var name = row.fileName;
      var url = row.fileUrl;
      var suffix = url.substring(url.lastIndexOf("."), url.length)
      const a = document.createElement('a')
      a.setAttribute('download', name)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', process.env.VUE_APP_BASE_API + url)
      a.click()
    },
    handleSee(row) {
      this.pdf.open = true
      this.pdf.title = row.fileName
      this.pdf.pdfUrl = process.env.VUE_APP_BASE_API + '/pdf/web/viewer.html?file=' + process.env.VUE_APP_BASE_API + row.fileUrl
    },
    openPdf() {
      window.open(this.pdf.pdfUrl);//path是文件的全路径地址
    },
    // 导出图片
    exportImg() {
      const elementToExport = document.getElementById('branch').getElementsByTagName("ul")[0].getElementsByTagName("li")[0]; // 选择要导出的HTML元素

      html2canvas(elementToExport).then(canvas => {
        canvas.toBlob(blob => {
          // const url = URL.createObjectURL(blob);

          const downloadLink = document.createElement('a')
          downloadLink.href = canvas.toDataURL('image/png'); // 将Canvas或图片转换为数据URL
          downloadLink.download = 'image.png'; // 设置下载的文件名

// 模拟点击下载链接
          downloadLink.click();
        });
      });
    }
  }
}
</script>
<style lang="scss" scoped scoped>
.imgfr {
  margin-right: 10px;
}
.iconfont{
  color: #ff0000;
}
</style>
