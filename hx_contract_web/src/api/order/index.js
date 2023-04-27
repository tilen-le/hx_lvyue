import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/ruoyi";

export function listOrder(query) {
  return request({
    url: '/order/list',
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
