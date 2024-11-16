import { inject } from "@angular/core";
import { HttpInterceptorFn } from "@angular/common/http";
import {UserDataService} from "../auth/user.data.service";

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(UserDataService).getToken();
// console.log("fffff: ", req)
  // const request = req.clone({
  //   setHeaders: {
  //     ...(token ? { Authorization: `Token ${token}` } : {}),
  //   },
  // });
  return next(req);
};
