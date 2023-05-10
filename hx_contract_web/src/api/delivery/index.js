import request from '@/utils/request'

// 获取发货单详情
export function getDeliveryApi(query){
  return request({
    url: '/delivery/detail',
    method: 'get',
    params: query
  })
}
