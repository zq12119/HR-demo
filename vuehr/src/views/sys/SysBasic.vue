<template>
  <div>
    <!-- v-nodel-activeNaem双向数据用于数据之间的切换 -->
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="部门管理" name="first">
        <Department></Department>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="second">
        <Position></Position>
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="third">
        <JobLevel></JobLevel>
      </el-tab-pane>
      <el-tab-pane label="奖惩管理" name="fourth">
        <Reward></Reward>
      </el-tab-pane>
      <el-tab-pane label="权限组" name="five">
        <Permission></Permission>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
// 导入子组件
import Department from '../../component/sys/basic/Department'
import JobLevel from '../../component/sys/basic/JobLevel'
import Permission from '../../component/sys/basic/Permission'
import Position from '../../component/sys/basic/Position'
import Reward from '../../component/sys/basic/Reward'
export default {
  name: 'SysBasic',
  data() {
    return {
      //    第二个界面
      activeName: 'second'
    }
  },
  components: {
    Department,
    Position,
    JobLevel,
    Reward,
    Permission
  }
}
</script>

<style scoped>
</style>