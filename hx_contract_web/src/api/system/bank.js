import request from '@/utils/request'
export function addBank(data) {
  return request({
    url: '/system/saleBank',
    method: 'post',
    data: data
  })
}
export function updateBank(data) {
  return request({
    url: '/system/saleBank',
    method: 'put',
    data: data
  })
}
export function listBank(data) {
  return request({
    url: '/system/saleBank/list',
    method: 'get',
    params:data
  })
}


export function listAvailableBank() {
  return request({
    url: '/system/saleBank/available',
    method: 'get'
  })
}
