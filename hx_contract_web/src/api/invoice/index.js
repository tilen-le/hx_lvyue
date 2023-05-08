import request from '@/utils/request'
export function addInvoice(data) {
  return request({
    url: '/invoice/add',
    method: 'post',
    data: data
  })
}
export function addDelivery(data) {
  return request({
    url: '/order/consignment/add',
    method: 'post',
    data: data
  })
}
export function listInvoice(query) {
  return request({
    url: '/invoice/list',
    method: 'get',
    params: query
  })
}
