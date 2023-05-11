package com.hexing.common.utils;

import com.hexing.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import static com.hexing.common.constant.CacheConstants.GEN_SHIPPING_PLAN_CODE;


@Component
@RequiredArgsConstructor
public class CodeGenerate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final int DEFAULT_LENGTH = 5;

    /**
     * 利用redis生产发货计划code
     *
     * @return
     */
    public String genShippingPlanCode() {
        try {
            Long incr = redisTemplate.opsForValue().increment(GEN_SHIPPING_PLAN_CODE);
            String seq = getSequence(incr);
            StringJoiner sj = new StringJoiner("");
            sj.add("IED-").add(seq);
            String planCode = sj.toString();
            if (StringUtils.isNotEmpty(planCode)) {
                return planCode;
            } else {
                throw new ServiceException("生成盘点计划单号失败");
            }
        } catch (Exception e) {
            throw new ServiceException("生成盘点计划单号异常: " + e.getMessage());
        }

    }


    /**
     * 按照key值的长度获取序号
     *
     * @param seq
     * @return
     */
    public static String getSequence(Long seq) {
        String str = String.valueOf(seq);
        int len = str.length();
        if (len >= DEFAULT_LENGTH) {
            return str;
        }
        int rest = DEFAULT_LENGTH - len;
        StringJoiner stringJoiner = new StringJoiner("");
        for (int i = 0; i < rest; i++) {
            stringJoiner.add("0");
        }
        stringJoiner.add(str);
        return stringJoiner.toString();
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        return df.format(localDate);
    }
}
