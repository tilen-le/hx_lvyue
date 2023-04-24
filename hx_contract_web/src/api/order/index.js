import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/ruoyi";

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query
  })
}
