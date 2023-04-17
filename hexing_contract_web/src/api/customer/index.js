import request from '@/utils/request'

export function listCustomer(query) {
  return request({
    url: '/system/customer/page',
    method: 'get',
    params: query
  })
}
