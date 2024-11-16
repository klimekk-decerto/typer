import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class MainPageService {

  constructor(private http: HttpClient) {
  }

  test2() {
    // console.log("ssss");
    // const httpOptions = {
    //   headers: new HttpHeaders({
    //     'Authorization': 'Basic ' + btoa('user_1:user_1')
    //   }),
    //   withCredentials: true
    // }
    // return this.http.get<any>('app/auth/login', httpOptions).subscribe();
    return this.http.get('app/api/test').subscribe();
  }
}
