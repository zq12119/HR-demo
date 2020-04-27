import {
  getRequest
} from './api'
export const initMenu = (router, store) => {
  // store的state有数据就不请求
  if (store.state.routes.length > 0) {
    return
  }
  getRequest('/system/config/menu').then(data => {
    if (data) {
      let fmtRoutes = formatRoutes(data)
      router.addRoutes(fmtRoutes)
      store.commit('initRoutes', fmtRoutes)
    }
  })
}
export const formatRoutes = (routes) => {
  let menuRoutes = [];
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      meta,
      iconCls,
      children
    } = router
    if (children && children instanceof Array) {
      children = formatRoutes(children)
    }
    let menuRouter = {
      path: path,
      name: name,
      iconCls: iconCls,
      meta: meta,
      children: children,
      component(resolve) {
        if (component.startsWith("Home")) {
          require(['../views/' + component + '.vue'], resolve) // 懒加载
        } else if (component.startsWith("Emp")) {
          require(['../views/emp/' + component + '.vue'], resolve)
        } else if (component.startsWith("Per")) {
          require(['../views/per/' + component + '.vue'], resolve)
        } else if (component.startsWith("Sal")) {
          require(['../views/sal/' + component + '.vue'], resolve)
        } else if (component.startsWith("Sta")) {
          require(['../views/sta/' + component + '.vue'], resolve)
        } else if (component.startsWith("Sys")) {
          require(['../views/sys/' + component + '.vue'], resolve)
        }
      }
    }
    menuRoutes.push(menuRouter)
  })
  return menuRoutes
}