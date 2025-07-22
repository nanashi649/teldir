package jp.co.ridi.teldir.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler  {
	@ExceptionHandler(OptimisticLockingFailureException.class)
	//返却値をjsonにする
	@ResponseBody
	//競合エラー409
    @ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleOptimisticError(OptimisticLockingFailureException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("optimisticError", ex.getMessage());
        return error;
    }
}
