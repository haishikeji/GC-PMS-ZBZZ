<template>
  <div>
    <el-button
      v-if="this.meetingId == null"
      style="margin: 15px 1px 1px 35px;"
      type="success"
      icon="el-icon-edit"
      size="mini"
      @click="handleAdd"
      v-hasPermi="['plant:keymaintenance:edit']"
    >保存会议
    </el-button>
    <el-button
      type="primary"
      v-if="this.meetingId == null"
      style="margin: 15px 1px 1px 35px;"
      icon="el-icon-s-order"
      size="mini"
      @click="openDialog"
    >历史行动项管理
    </el-button>
    <h3
      v-if="this.meetingId != null"
      style="margin: 15px 1px 1px 35px;"
    >会议内容</h3>

    <el-tabs tab-position="left" class="app-container">
      <el-tab-pane label="参会人员">
        <person :meetingId="meetingId"/>
      </el-tab-pane>
      <el-tab-pane label="内容">
        <Content :meetingId="meetingId"/>
      </el-tab-pane>
    </el-tabs>

    <!-- 添加或修改装置会议对话框 -->
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主题" prop="subject">
          <el-input v-model="form.subject" placeholder="请输入主题"/>
        </el-form-item>
        <el-form-item label="主持人" prop="presided">
          <el-input v-model="form.presided" placeholder="请输入主持人"/>
        </el-form-item>
        <el-form-item label="记录人" prop="recorder">
          <el-input v-model="form.recorder" placeholder="请输入记录人"/>
        </el-form-item>
        <el-form-item label="会议编号" prop="meetingNo">
          <el-input v-model="form.meetingNo" placeholder="请输入 +会议编号"/>
        </el-form-item>
        <el-form-item label="会议时间" prop="meetingDate">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.meetingDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择会议时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="会议地点" prop="venue">
          <el-input v-model="form.venue" placeholder="请输入会议地点"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :close-on-click-modal="false" title="历史行动项管理" :visible.sync="his.open" width="70%" append-to-body>
      <el-form :model="his.query" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="回顾/截止日期" prop="deadlineTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="his.query.deadlineTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择回顾/截止日期" @change="getContentList">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="类型" prop="workType" label-width="50px">
          <el-select v-model="his.query.workType" placeholder="请选择类型" clearable size="small"
                     style="width: 100%;" @change="getContentList">
            <el-option
              v-for="dict in workTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status" label-width="50px">
          <el-select v-model="his.query.status" placeholder="请选择状态" clearable size="small"
                     style="width: 100%;" @change="getContentList">
            <el-option
              v-for="dict in agendaStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="跟踪人" prop="responsible" label-width="70px">
          <el-input v-model="his.query.responsible" placeholder="请输入跟踪人" />
        </el-form-item>
        <el-form-item>
          <el-button type="cyan" icon="el-icon-search" size="mini" @click="getContentList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getContentList"></right-toolbar>
      </el-row>

      <el-table :data="his.contentList" :height="clientHeight" border>
        <el-table-column label="主题" align="center" prop="subject"/>
        <el-table-column label="会议编号" align="center" prop="meetingNo"/>
        <el-table-column label="会议时间" align="center" prop="meetingDate">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.meetingDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="内容" align="center" prop="agendaContent">
          <template slot-scope="scope">
            <el-input type="textarea" v-model="scope.row.agendaContent" placeholder="请输入行动项内容" v-if="scope.row.isEdit&&scope.row.workType==='P'"/>
            <span v-else>{{ scope.row.agendaContent }}</span>
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" prop="workType"/>
        <el-table-column label="跟踪人" align="center" prop="responsible"/>
        <el-table-column label="回顾/截止时间" align="center" prop="deadlineTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deadlineTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-select v-if="scope.row.isEdit" v-model="scope.row.status" placeholder="请选择状态"
                       clearable
                       size="small" style="width: 100%;">
              <el-option
                v-for="dict in agendaStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
            <span v-else>{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              v-if="!scope.row.isEdit&&scope.row.workType!=='I'"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdateRecord(scope.row)"
              v-hasPermi="['plant:content:edit']"
            >修改
            </el-button>
            <el-button v-if="scope.row.isEdit"
                       size="mini"
                       type="text"
                       icon="el-icon-check"
                       @click="save(scope.row)"
            >保存
            </el-button>
            <el-button v-if="scope.row.isEdit"
                       size="mini"
                       type="text"
                       icon="el-icon-close"
                       @click="cancelRecord(scope.row,scope.$index)"
            >取消
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="his.total>0"
        :total="his.total"
        :page.sync="his.query.pageNum"
        :limit.sync="his.query.pageSize"
        @pagination="getContentList"
      />
    </el-dialog>
  </div>
</template>

<script>
import {addMeeting, getMeeting, updateMeeting} from "@/api/plant/meeting";
import Treeselect from "@riophae/vue-treeselect";
import {treeselect} from "@/api/system/dept";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";
import Content from "@/views/plant/content/index.vue";
import Person from "@/views/components/meeting/person.vue";
import {listContent2, updateContent} from "@/api/plant/content";

export default {
  name: "Weekmeeting",
  components: {
    Content,
    Person,
    Treeselect
  },
  data() {
    return {
      workTypeOptions: [],
      agendaStatusOptions: [],
      his: {
        open: false,
        contentList: [],
        total: 0,
        query: {
          pageNum: 1,
          pageSize: 10,
          isHis: 1,
        }
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 装置会议表格数据
      meetingList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      // 装置字典
      plantCodeOptions: [],
      // 表单参数
      form: {},
      meetingId: 0,
      // 附件参数
      doc: {
        file: "123",
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
          pType: 'manageactivity'
        },
        pType: 'manageactivity',
        pId: null
      },
      // 表单校验
      rules: {
        subject: [
          {required: true, message: '会议主题不能为空', trigger: "change"}
        ],
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.meetingId = this.$route.params && this.$route.params.tableId;
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8
    })
    this.getTreeselect();
    this.getDicts("PLANT_DIVIDE").then(response => {
      this.plantCodeOptions = response.data;
    });
    this.getDicts("agenda_status").then(res => {
      this.agendaStatusOptions = res.data;
    });
  },
  methods: {
    save(row) {
      let data = {
        id: row.id,
        status: row.status
      }
      updateContent(data).then(res => {
        this.$modal.msgSuccess("修改成功");
        row.isEdit = false;
        this.getContentList();
      })
    },
    handleUpdateRecord(row) {
      // 备份原始数据
      row['oldRow'] = JSON.parse(JSON.stringify(row));
      this.$nextTick(() => {
        row.isEdit = true;
      })
    },
    cancelRecord(row, index) {
      // 如果是新增的数据
      if (row.isAdd) {
        this.record.dataList.splice(index, 1)
      } else {
        // 不是新增的数据  还原数据
        for (const i in row.oldRow) {
          row[i] = row.oldRow[i]
        }
        row.isEdit = false
      }
    },
    openDialog() {
      this.his.open = true;
      this.getContentList();
      this.getDicts("agenda_type").then(res => {
        this.workTypeOptions = res.data;
      });
      this.getDicts("agenda_status").then(res => {
        this.agendaStatusOptions = res.data;
      });
    },
    getContentList() {
      listContent2(this.his.query).then(response => {
        response.rows.forEach(item => {
          item["isEdit"] = false;
        })
        this.his.contentList = response.rows;
        this.his.total = response.total;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 装置字典翻译
    plantCodeFormat(row, column) {
      return this.selectDictLabel(this.plantCodeOptions, row.plantCode);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        plantCode: null,
        recorder: null,
        meetingNo: null,
        meetingDate: null,
        venue: null,
        subject: null,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        presided: null,
        mtType: 2,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = '新增装置会议';
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMeeting(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = '修改装置会议';
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMeeting(this.form).then(response => {
              this.$modal.msgSuccess('修改成功');
              this.open = false;
              window.location.reload();
            });
          } else {
            addMeeting(this.form).then(response => {
              this.$modal.msgSuccess('新增成功');
              this.open = false;
             location.reload();
            });
          }
        }
      });
    },
    /** 报告附件按钮操作 */
    importMeeting(row) {
      this.doc.open = true;
      this.$nextTick(() => {
        this.$refs.doc.clearFiles()
      })
    },
  }
};
</script>

<style>

</style>
