package ua.social.network.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import ua.social.network.response.BaseResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Mykola Yashchenko
 */
@Aspect
@Component
public class ValidationAspect {

    @Autowired
    private Validator validator;

    @Around("execution(* ua.social.network.service..*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] argAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            final Object argument = args[i];

            for (Annotation annotation : argAnnotations[i]) {
                if (RequestBody.class.isInstance(annotation)) {
                    BindingResult errors = new BeanPropertyBindingResult(argument, argument.getClass().getSimpleName());
                    validator.validate(argument, errors);
                    if (errors.hasErrors()) {
                        return ResponseEntity.badRequest().body(new BaseResponse("ERROR"));
                    }
                }
            }
        }

        return joinPoint.proceed();
    }
}
