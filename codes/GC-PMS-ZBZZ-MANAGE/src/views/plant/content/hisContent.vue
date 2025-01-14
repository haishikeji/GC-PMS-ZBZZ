<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="his.query" label-width="100px">
      <el-form-item label="回顾/截止日期" prop="deadlineTime">
        <el-date-picker v-model="his.query.deadlineTime" clearable placeholder="请选择回顾/截止日期"
                        size="small"
                        style="width: 200px"
                        type="date"
                        value-format="yyyy-MM-dd" @change="getContentList">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类型" label-width="50px" prop="workType">
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
      <el-form-item label="状态" label-width="50px" prop="status">
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
      <el-form-item label="跟踪人" label-width="70px" prop="responsible">
        <el-input v-model="his.query.responsible" placeholder="请输入跟踪人"/>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="cyan" @click="getContentList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getContentList"></right-toolbar>
    </el-row>

    <el-table :data="his.contentList" :height="clientHeight" border>
      <el-table-column align="center" label="主题" prop="subject"/>
      <el-table-column align="center" label="会议编号" prop="meetingNo"/>
      <el-table-column align="center" label="会议时间" prop="meetingDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.meetingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="内容" prop="agendaContent">
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit&&scope.row.workType==='P'" v-model="scope.row.agendaContent"
                    placeholder="请输入行动项内容"
                    type="textarea"/>
          <span v-else>{{ scope.row.agendaContent }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="类型" prop="workType"/>
      <el-table-column align="center" label="跟踪人" prop="responsible"/>
      <el-table-column align="center" label="回顾/截止时间" prop="deadlineTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deadlineTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status">
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
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="!scope.row.isEdit&&scope.row.workType!=='I'"
            v-hasPermi="['plant:content:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdateRecord(scope.row)"
          >修改
          </el-button>
          <el-button v-if="scope.row.isEdit"
                     icon="el-icon-check"
                     size="mini"
                     type="text"
                     @click="save(scope.row)"
          >保存
          </el-button>
          <el-button v-if="scope.row.isEdit"
                     icon="el-icon-close"
                     size="mini"
                     type="text"
                     @click="cancelRecord(scope.row,scope.$index)"
          >取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="his.total>0"
      :limit.sync="his.query.pageSize"
      :page.sync="his.query.pageNum"
      :total="his.total"
      @pagination="getContentList"
    />
  </div>
</template>

<script>
import {listContent2, updateContent} from "@/api/plant/content";

export default {
  name: "hisContent",
  data() {
    return {
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
      // 显示搜索条件
      showSearch: false,
      clientHeight: 300,
      workTypeOptions: [],
      agendaStatusOptions: [],
      plantCodeOptions: [],
    }
  },
  created() {
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8
    })
    this.getContentList();
    this.getDicts("PLANT_DIVIDE").then(response => {
      this.plantCodeOptions = response.data;
    });
    this.getDicts("agenda_type").then(res => {
      this.workTypeOptions = res.data;
    });
    this.getDicts("agenda_status").then(res => {
      this.agendaStatusOptions = res.data;
    });
  },
  methods: {
    save(row) {
      let data = {
        id: row.id,
        status: row.status,
        agendaContent: row.agendaContent,
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
    getContentList() {
      listContent2(this.his.query).then(response => {
        response.rows.forEach(item => {
          item["isEdit"] = false;
        })
        this.his.contentList = response.rows;
        this.his.total = response.total;
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
}
</script>

<style scoped>

</style>
