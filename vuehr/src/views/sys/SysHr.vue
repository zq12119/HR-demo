<template>

  <div>
    <div style="margin-top:10px;display:flex;justify-content:center">
      <el-input v-model="keywords" placeholder="通过用户名搜索用户" prefix-icon="el-icon-search"
      style="width:400px; margin-right:10px" @keydown.enter.native="doSearch"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="doSearch">搜索</el-button>
    </div>

    <div class="container">
      <el-card class="hr-card" v-for="(hr,index) in hrs" :key="index">
        <div slot="header" class="clearfix">
          <span>{{hr.name}}</span>
          <el-button type="text" style="float:right; padding:3px 0;color:#e30007;"
          icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
        </div>

<div>
  <div class="img-container">
    <img:src="hr.userface" :alt="hr.name" :title="hr.name" class="userface-img">
  </div>
  <div class="userinfo-container">
    <div>用户名：{{hr.name}}</div>
    <div>手机号码：{{hr.phone}}</div>
    <div>电话号码：{{hr.telephone}}</div>
    <div>地址：{{hr.address}}</div>
    <div>用户状态：
      <el-switch v-model="hr.enabled" active-text="启用" @change="enabledChange(hr)"
      active-color="#13ce66" inactive-color="#ff4949" inactive-text="禁用">
      </el-switch>
    </div>
    <div>
      用户角色：
      <el-tag type="success" style="margin-right:5px" v-for="(role,index) in hr.roles"
      :key="index">{{role.nameZh}}
      </el-tag>
      <el-popover placement="right" title="角色列表" @show="showPop(hr)" @hide="hidePop(hr)"
      width="200" trigger="click">
     <el-select v-model="selectedRoles" multiple placeholder="请选择">
       <el-option v-for="(r,indexk) in allroles"
       :key="indexk" :label="r.nameZh" :value="r.id">
       </el-option>
     </el-select>
     <el-button type="text" slot="reference" icon="el-icon-more"></el-button>
      </el-popover>
    </div>
    <div>备注：{{hr.name}}</div>
  </div>
</div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SysHr',
  data() {
    return {}
  }
}
</script>

<style scoped>
</style>