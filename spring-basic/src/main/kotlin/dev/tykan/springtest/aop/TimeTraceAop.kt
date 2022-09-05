package dev.tykan.springtest.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeTraceAop {

    @Throws(Throwable::class)
    @Around("execution(* dev.tykan.springtest..*(..))")
    fun excute(joinPoint: ProceedingJoinPoint): Any? {
        val start: Long = System.currentTimeMillis()
        println("Start: $joinPoint")
        try {
            return joinPoint.proceed()
        } finally {
            val timeMs = System.currentTimeMillis() - start
            println("END: $joinPoint $timeMs ms")
        }
    }
}