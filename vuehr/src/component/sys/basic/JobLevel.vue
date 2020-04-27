<template>
  <div>
    <div>
      <el-input
        v-model="jobLevel.name"
        placeholder="添加职称"
        size="small"
        class="input_type"
        prefix-icon="el-icon-plus"
      ></el-input>
      <el-select v-model="jobLevel.titlelevel" placeholder="职称等级" size="small" class="select_type">
        <el-option :label="item" :value="item" :key="item" v-for="item in titleLevels"></el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-plus" size="small" @click="addJobLevel">添加</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table
        class="table"
        :data="jobLevels"
        stripe
        border
        type="mini"
        style="width: 88%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="70"></el-table-column>
        <el-table-column prop="id" label="编号" width="70"></el-table-column>
        <el-table-column prop="name" label="职称名称" width="140"></el-table-column>
        <el-table-column prop="titlelevel" label="职称级别" width="140"></el-table-column>
        <el-table-column prop="createdate" label="创建时间" width="140"></el-table-column>
        <el-table-column prop="enabled" label="是否启用" width="150">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
            <el-tag v-else type="warning">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="showEditDialog(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button
        type="danger"
        size="small"
        style="margin-top: 10px"
        @click="deleteMany"
        :disabled="multipleSelection.length === 0"
      >批量删除</el-button>
    </div>
    <el-dialog title="修改职称" :visible.sync="dialogVisible" width="35%">
      <div>
        <div>
          <el-tag>职称名称</el-tag>
          <el-input class="update_select" size="small" v-model="updateJobLevel.name"></el-input>
        </div>
        <div>
          <el-tag>职称级别</el-tag>
          <el-select
            v-model="updateJobLevel.titlelevel"
            placeholder="级别"
            size="small"
            class="update_select"
          >
            <el-option v-for="item in titleLevels" :key="item" :label="item" :value="item"></el-option>
          </el-select>
        </div>
        <div>
          <el-tag>是否启用</el-tag>
          <el-switch v-model="updateJobLevel.enabled" size="small" class="update_select"></el-switch>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取消</el-button>
        <el-button type="primary" @click="doUpdate" size="small">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'JobLevel',
  data() {
    return {
      jobLevel: {
        name: '',
        titlelevel: ''
      },
      titleLevels: ['正高级', '副高级', '中级', '初级', '员级'],
      jobLevels: [],
      updateJobLevel: {
        name: '',
        titlelevel: '',
        enabled: true
      },
      // 显示对话框标志位
      dialogVisible: false,
      // 批量删除
      multipleSelection: []
    }
  },
  mounted() {
    this.initJobLevels()
  },
  methods: {
    // 表格数据初始化处理
    async initJobLevels() {
      const data = await this.getRequest('/system/basic/joblevel/')
      if (data) {
        this.jobLevels = data.obj
      }
    },
    // 添加新记录的事件处理
    async addJobLevel() {
      if (this.jobLevel.name && this.jobLevel.titlelevel) {
        const resp = await this.postRequest(
          '/system/basic/joblevel/',
          this.jobLevel
        )
        if (resp) {
          this.initJobLevels()
          this.jobLevel.name = ''
          this.jobLevel.titlelevel = ''
        }
      } else {
        this.$message.error('职称名称和等级不能为空')
      }
    },
    // 显示修改对话框
    showEditDialog(index, data) {
      Object.assign(this.updateJobLevel, data)
      // 使用深拷贝
      this.dialogVisible = true
    },
    // 弹框确认修改的事件处理
    async doUpdate() {
      const resp = await this.putRequest(
        '/system/basic/joblevel/',
        this.updateJobLevel
      )
      if (resp) {
        this.initJobLevels()
        this.dialogVisible = false
      }
    },
    // 表格记录的删除按钮的事件处理
    handleDelete(index, data) {
      this.$confirm(
        '此操作将永久删除' + data.name + '职称, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.deleteRequest('/system/basic/joblevel/' + data.id).then(resp => {
            this.initJobLevels()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
    },
    // 记录多选的处理
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    // 批量删除
    deleteMany() {
      this.$confirm(
        '此操作将永久删除' +
          this.multipleSelection.length +
          '条记录, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          // 生成删除记录 id的查询字符串
          let ids = '?'
          this.multipleSelection.forEach(item => {
            ids += 'ids=' + item.id + '&'
          })
          this.deleteRequest('/system/basic/joblevel/' + ids).then(resp => {
            this.initJobLevels()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
    }
  }
}
</script>

<style scoped>
.input_type {
  width: 300px;
  margin-right: 8px;
}
.select_type {
  width: 200px;
  margin-right: 8px;
}
.update_select {
  width: 200px;
  margin: 8px;
}
.table {
  text-align-last: center;
}
</style>