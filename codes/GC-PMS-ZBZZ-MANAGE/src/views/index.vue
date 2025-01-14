<template>
  <div :style="{height:clientHeight,width:'100%'}" class="bg">
    <div class="glass_div">
      <el-carousel :autoplay="false" :height="clientHeight" direction="vertical">
        <el-carousel-item>
          <table style="width: 100%;height: 100%;text-align: center;">
            <tr>
              <td rowspan="3" style="width: 80%;">
                <div>
                  <el-select v-model="activeItem" style="width: 30%;" @change="changeIndex">
                    <el-option :value="0" label="项目相关"/>
                    <el-option :value="1" label="AE"/>
                    <el-option :value="2" label="资产管理"/>
                    <el-option :value="3" label="学习平台"/>
                    <el-option :value="4" label="BASF4.0"/>
                    <el-option :value="5" label="IT用"/>
                    <el-option :value="6" label="环保"/>
                    <el-option :value="7" label="化工区入场"/>
                    <el-option :value="8" label="安全"/>
                    <el-option :value="9" label="文控"/>
                    <el-option :value="10" label="未分类"/>
                  </el-select>
                </div>
                <el-carousel ref="carousel" :autoplay="false" height="400px"
                             style="padding: 30px;" trigger="click" @change="carouselChange" @setActiveItem="setActiveItem">
                  <el-carousel-item>
                    <url-icon name="项目月度费用报告"
                              url="https://z2pr3.basf.net:44398/sap(bD1lbiZjPTEwMCZkPW1pbg==)/bc/bsp/sap/yrdw_bexweb/workset.htm?projekt=BEXWEB_LOGIN"/>
                    <url-icon name="汇率表"
                              url="https://app.powerbi.com/groups/me/apps/0be7009d-055c-4635-aa69-b1ca896acfce/reports/b50af5bc-57d5-4866-8537-d00bbd17e7b0/ReportSection9c27620626ccd4de907b?ctid=ecaa386b-c8df-4ce0-ad01-740cbdb5ba55&experience=power-bi&bookmarkGuid=Bookmark76f8e220a58b00d14bd9"/>
                    <url-icon name="ProCID Dashboard"
                              url="https://app.powerbi.com/groups/me/reports/f19abff6-3d90-4b73-9c73-8d1ed158d4f7/ReportSectionccb625529076aa55aea7?experience=power-bi"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="AE 数据"
                              url="https://app.powerbi.com/groups/me/reports/c56f4011-c30c-407b-8850-6a8869657ed8/ReportSection5c7cc43b5b5e4f381f65?experience=power-bi"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="Asset Management Dashboard GC - Power BI"
                              url="https://oa.basf.com/wui/index.html#/main/portal/portal-1-1?menuIds=0,1&menuPathIds=0,1&_key=t13jkl"/>
                    <url-icon name="资产新增 报废 转移平台"
                              url="https://app.powerbi.com/groups/me/reports/99ffb08d-2ece-4610-9896-44d0f80e7038/ReportSection60758fabfc1198291987?ctid=ecaa386b-c8df-4ce0-ad01-740cbdb5ba55&experience=power-bi"/>
                    <url-icon name="Manufacturing Dashboard by CDT - Power BI"
                              url="https://basf.sharepoint.com/sites/materials-engineering-ndt-asia/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2Fmaterials%2Dengineering%2Dndt%2Dasia%2FShared%20Documents%2Fsite%20support%2FBACH%2FTHF&p=true&originalPath=aHR0cHM6Ly9iYXNmLnNoYXJlcG9pbnQuY29tLzpmOi9zL21hdGVyaWFscy1lbmdpbmVlcmluZy1uZHQtYXNpYS9FaEwyQlljYWluSk5udjdWMmdpR0tja0JuUTJJSGh6VkFpZUZhTDRCdTFZQ2FRP3J0aW1lPXdvaERRSzZkMkVn"/>
                    <url-icon name="历年GEA 设备评估汇总"
                              url="https://basf.sharepoint.com/sites/materials-engineering-ndt-asia/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2Fmaterials%2Dengineering%2Dndt%2Dasia%2FShared%20Documents%2Fsite%20support%2FBACH%2FTHF&p=true&originalPath=aHR0cHM6Ly9iYXNmLnNoYXJlcG9pbnQuY29tLzpmOi9zL21hdGVyaWFscy1lbmdpbmVlcmluZy1uZHQtYXNpYS9FaEwyQlljYWluSk5udjdWMmdpR0tja0JuUTJJSGh6VkFpZUZhTDRCdTFZQ2FRP3J0aW1lPXdvaERRSzZkMkVn"/>
                    <url-icon name="Manufacturing Dashboard"
                              url="https://app.powerbi.com/groups/me/reports/fe13f474-ece7-4cc7-a3f7-c7b91723d606/ReportSection97b3afab401a7e531ae9?ctid=ecaa386b-c8df-4ce0-ad01-740cbdb5ba55&openReportSource=ReportInvitation&experience=power-bi"/>
                    <url-icon name="特种设备隐患排查" url="https://yhpc.scjgj.sh.gov.cn/Default.jsp?url=%2fStart.jsp"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="工程用 guidline 平台"
                              url="https://intranet.basf.com/global-engineering/welcome-to-global-engineering-services/rules-and-standards/"/>
                    <url-icon name="GC Learning & Development"
                              url="https://basf.sharepoint.com/teams/GCLearningDevelopment"/>
                    <url-icon name="apehs-academy" url="https://apehs-academy.basf.com/"/>
                    <url-icon name="BASF guidline 平台"
                              url="https://confluence.basf.net/display/CRB/Corporate+Rule+Base"/>
                    <url-icon name="道安合规性平台" url="https://gcrccs.basf.com/Enterprise/Home/Index.cshtml"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="BACH Smart EMS V1 0_PTHFC - Power BI"
                              url="https://app.powerbi.com/groups/me/reports/911e32fc-e7a5-486f-abbc-cfd4afa67bbe/ReportSectiona0389fd734ddb0403ae2?openReportSource=ReportInvitation&ctid=ecaa386b-c8df-4ce0-ad01-740cbdb5ba55&experience=power-bi"/>
                    <url-icon name="Dashboard"
                              url="https://app.powerbi.com/groups/me/reports/7ce0cbfe-214c-44e7-b1c5-fa72d20716b5/ReportSection19825d05b55041ae10d9?ctid=ecaa386b-c8df-4ce0-ad01-740cbdb5ba55&experience=power-bi"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="AccessIT 权限用" url="https://accessit.basf.net/irj/portal/accessit"/>
                    <url-icon name="Service4You 软件用" url="https://service4you.intranet.basf.com/esc"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="环保国控平台" url="http://qymonitor.envsc.cn/amOnline/app/AppLogin.page"/>
                    <url-icon name="市环境监测中心" url="https://wry.semc.com.cn:4343/SHZXEnv30Amcs/"/>
                    <url-icon name="化工区环保平台" url="http://116.246.5.9:8181/loginPro_yc/login/login"/>
                    <url-icon name="公司环保平台" url="http://101.132.186.0:8011/EPP/"/>
                    <url-icon name="公司危废平台" url="https://cw-wms-dev.basf.com/basftitan/"/>
                    <url-icon name="国网危废平台" url="http://61.129.59.16:31001/shLogin.jsp"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="化工区入场管理" url="https://www.i-scip.com/login.html"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="电子作业票系统" url="https://bachepermit.basf.com/vweb/"/>
                    <url-icon name="电子黄卡系统" url="https://ehs-web.shincloud.com/"/>
                    <url-icon name="公司安全管理平台" url="https://bachcsm.basf.com/HSSEWEB/Login/Login"/>
                    <url-icon name="E-reporting" url="http://47.100.197.226/login"/>
                    <url-icon name="Statue" url="https://stature-prod.basf.net/stature/control/statureMain"/>
                    <url-icon name="HR Kiosk"
                              url="https://hp1r3.bcs.de/irj/servlet/prt/portal/prtroot/pcd!3aportal_content!2fcom.basfits.hr!2fgl!2flogon_page!2fcom.basfits.hr.global.iv.ep.java.login?ume.logon.locale=zh_TW&region=lu&bandwith=medium&ip=10.12.20.4&letitsnow=false"/>
                    <url-icon name="公司SDS数据库" url="https://sds.basf.com/"/>
                    <url-icon name="GID" url="https://global-incident-database.basf.net/GID/"/>
                    <url-icon name="双预防系统" url="http://whpscyf.com:8030/"/>
                    <url-icon name="化工区精细化消防平台" url="http://116.246.5.33:9000/company/"/>
                    <url-icon name="电子运单核验用" url="http://183.194.248.205:9637/login"/>
                    <url-icon name="市隐患排查平台" url="https://app.yjglj.sh.gov.cn/xzsp/index.jsp"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="Sharepoint已发布"
                              url="https://basf.sharepoint.com/sites/bachqmsharepoint/Published Documents/Forms/AllItems.aspx?RootFolder=%2Fsites%2Fbachqmsharepoint%2FPublished%20Documents%2F06%20Plant%2F01%20PTHF%20Complex%20Plant&FolderCTID=0x0120006AB086593152374FA27901EA1157B6AA&View=%7BC8AB33D6%2D66A3%2D4C2A%2DB3F7%2D6BA8FDD2CEDD%7D"/>
                    <url-icon name="Sharepoint批准用"
                              url="https://basf.sharepoint.com/sites/bachqmsharepoint/Approval Document/Forms/AllItems.aspx?RootFolder=%2Fsites%2Fbachqmsharepoint%2FApproval%20Document%2F06%20Plant%2F01%20PTHF%20Complex%20Plant&FolderCTID=0x01200020CBDBF18DD281438D5479EEBE05216C&View=%7B2B92CF08%2DED2F%2D4C9A%2D8188%2D077F49CFD652%7D"/>
                  </el-carousel-item>
                  <el-carousel-item>
                    <url-icon name="Opex管理用"
                              url="https://operations-database.basf.net/WebApp/OpExPlan/Default.aspx?&"/>
                    <url-icon name="LIMS" url="http://10.137.174.251/login.aspx"/>
                  </el-carousel-item>
                </el-carousel>
              </td>
            </tr>
            <tr>
              <th style="height: 30px">
                待办事项
              </th>
            </tr>
            <tr>
              <td>
              </td>
            </tr>
          </table>
        </el-carousel-item>
        <el-carousel-item>
          <el-calendar :style="{height:clientHeight,width:'95%',margin:'0 auto',color:'#3b3b3d'}"/>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import UrlIcon from "./urlIcon.vue";
export default {
  props: {
    clientHeight: {
      type: String,
      value: '300px'
    }
  },
  data() {
    return {
      activeItem: 0,
    };
  },
  components: {
    UrlIcon,
  },
  created() {
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 84).toString() + 'px'
    })
  },
  methods: {
    carouselChange(val) {
      this.activeItem = val
    },
    changeIndex(val) {
      this.setActiveItem(val);
    },
    setActiveItem(index) {
      this.$refs.carousel.setActiveItem(index);
    },
  }
};
</script>

<style>
.bg {
  background-image: url("../assets/images/bg.6900e4bf.jpg");
}

.glass_div {
  height: 100%;
  width: 100%;
  backdrop-filter: blur(10px);
  color: #fff;
}
</style>
