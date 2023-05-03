import request from '@/utils/request'
export function addInvoice(data) {
  return request({
    url: '/invoice/add',
    method: 'post',
    data: data
  })
}
