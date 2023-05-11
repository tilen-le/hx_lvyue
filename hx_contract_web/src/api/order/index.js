import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/ruoyi";

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单列表
export function listOrderByKeyWordApi(query) {
  return request({
    url: '/order/getOrderByNoOrName',
    method: 'get',
    params: query
  })
}

export function getOrderDetail(data) {
  return request({
    url: '/order/detailMap',
    method: 'post',
    data: data
  })
}



export function listCusOrder(query) {
  return request({
    url: '/order/getOrderByCusId',
    method: 'post',
    data: query
  })
}


export function listOrderDelivery(query) {
  return request({
    url: '/order/consignment/list',
    method: 'get',
    params: query
  })
}
