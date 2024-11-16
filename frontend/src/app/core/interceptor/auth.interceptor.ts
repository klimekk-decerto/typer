import {inject} from "@angular/core";
import {HttpInterceptorFn} from "@angular/common/http";
import {UserDataService} from "../auth/user.data.service";

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(UserDataService).getToken();

  if (token) {
    const reqWithToken = req.clone({
      setHeaders: {
        Authorization: `Basic ${token}`
      },
      withCredentials: true
    });
    return next(reqWithToken);
  }
  return next(req);
};
