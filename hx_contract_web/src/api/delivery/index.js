import request from '@/utils/request'

// 获取发货单详情
export function getDeliveryDetail(data){
  return request({
    url: '/order/consignment/check',
    method: 'post',
    data: data
  })
}
