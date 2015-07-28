package com.baidu.gcrm.notice.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.notice.web.vo.NoticeVo;

/**
 * @author shijiwen
 *
 */
public class NoticeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(NoticeVo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NoticeVo notice = (NoticeVo) target;

        if (StringUtils.isNotBlank(notice.getTitle()) && notice.getTitle().length() > 50) {
            errors.rejectValue("noticeVo.title", "noticeVo.title.length.toolong");
        }

    }
}
