import request from '@/utils/request'

// 获取发货单详情
export function getDeliveryDetail(data){
  return request({
    url: '/order/consignment/check',
    method: 'post',
    data: data
  })
}

// 发货单撤销审批
export function undoDeliveryApi(query){
  return request({
    url: '/delivery/detail',
    method: 'get',
    params: query
  })
}

export function approveDelivery(data) {
  return request({
    url: '/order/consignment/approve',
    method: 'post',
    data: data
  })
}
