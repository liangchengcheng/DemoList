package fwq.hdsx.com.injection;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by lcc on 16/4/27.
 */
//
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
