import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {
  }
  //
  // test() {
  //   return this.http.get('https://dog.ceo/api/breeds/image/random').subscribe();
  // }
  login() {

    // console.log("ssss");
    // const httpOptions = {
    //   headers: new HttpHeaders({
    //     'Authorization': 'Basic ' + btoa('user_1:user_1')
    //   }),
    //   withCredentials: true
    // }
    // return this.http.get<any>('app/auth/login', httpOptions).subscribe();
    return this.http.post<boolean>('app/auth/login', {login: 'user_1', password: 'user_1'}).subscribe();
  }

  test() {
    return this.http.get('https://dog.ceo/api/breeds/image/random').subscribe();
  }
}
