// 静态页面
<template>
  <div>
    <div>
      <el-input v-model="pos.name" placeholder="添加职位" size="small" class="input_type"
        prefix-icon="el-icon-plus" @keydown.enter.native="addPosition">
      </el-input>
      <el-button type="primary" icon="el-icon-plus" size="small" @click="addPosition">添加</el-button>
    </div>
    <div>
        <!-- 表格birder为true有边框 -->
      <el-table :data="positions" stripe border type="small" style="width: 70%" @selection-change="handleSelectionChange">
        <!-- 多选 -->
        <el-table-column type="selection" width="56"> </el-table-column>
        <el-table-column prop="id" label="编号" width="56"> </el-table-column>
        <el-table-column prop="name" label="职位名称" width="180"> </el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="200"> </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
              <!-- 多选事件触发 -->
            <el-button size="mini" @click="showEditDialog(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="danger" size="small" style="margin-top: 8px" @click="deleteMany"
                 :disabled="multipleSelection.length == 0"> 批量删除</el-button>
    </div>
    <el-dialog title="修改职位" :visible.sync="dialogVisible" width="30%">
      <div>
        <el-tag>职位名称</el-tag>
        <el-input class="update_input" size="small" v-model="updatePos.name"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" >取消</el-button>
        <el-button type="primary" @click="doUpdate" size="small" >确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Position',
  data () {
    return {
      // 添加输入框的数据封装成一个对象
      pos: {
        name: ''
      },
      //显示表格的数据
      positions: [],
      // 更新按钮的数据
      updatePos: {
        name: ''
      },
      // 对话框显示与否的标志位
      dialogVisible: false,
      // 批量删除的数据记录
      multipleSelection: []
    }
  },

  methods: {
    // 表格数据初始化处理
    async initPositions () {
      const data = await this.getRequest('/system/basic/pos/')
      if (data) {
        this.positions = data.obj
      }
    },
    // 添加新记录的事件处理
    async addPosition () {
      if (this.pos.name) {
        //   发生post请求如果能够找到把数据填入表格
        const resp = await this.postRequest('/system/basic/pos/', this.pos)
        if (resp) {
          this.initPositions()
          this.pos.name = ''
        }
      } else {
        this.$message.error('职位名称不能为空')
      }
    },
    // 显示修改对话框
    showEditDialog (index, data) {
      // 浅拷贝会改变表格的记录
      Object.assign(this.updatePos, data)  // 使用深拷贝
      this.dialogVisible = true
    },
    // 弹框确认修改的事件处理
    async doUpdate() {
      const resp = await this.putRequest('/system/basic/pos/', this.updatePos)
      if (resp) {
        this.initPositions()
        this.updatePos.name = ''
        this.dialogVisible = false
      }
    },
    // 表格记录的删除按钮的事件处理
    handleDelete (index, data) {
      this.$confirm('此操作将永久删除' + data.name + '职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/pos/' + data.id).then(resp => {
          this.initPositions()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    // 记录多选的处理
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    // 批量删除
    deleteMany() {
      this.$confirm('此操作将永久删除' + this.multipleSelection.length + '条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 生成删除记录 id的查询字符串
        let ids = "?"
        this.multipleSelection.forEach(item => {
          ids += "ids=" + item.id + '&'
        })
        this.deleteRequest('/system/basic/pos/' + ids).then(resp => {
          this.initPositions()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    }
  },
  // 在页面元素挂载后加载数据
  mounted () {
    this.initPositions()
  }
}
</script>

<style scoped>
  .input_type {
    width: 300px;
    margin-right: 8px;
    margin-bottom: 16px;
  }
  .update_input {
    width: 200px;
    margin-left: 8px;
  }
</style>