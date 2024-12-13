package org.melviz.dataset.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.melviz.dataset.date.TimeAmount;

/**
 * <p>JSR303 annotation implementation for <code>org.melviz.common.shared.validation.IsTimeInterval</code>.</p>
 */
public class IsTimeIntervalImpl implements ConstraintValidator<IsTimeInterval, String> {

    @Override
    public void initialize(IsTimeInterval constraintAnnotation) {
        // Do nothing.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && value.trim().length() > 0) {
            try {
                TimeAmount.parse(value);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

}
