import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserDataService} from "./user.data.service";

@Injectable()
export class AuthService {

  constructor(
    private http: HttpClient,
    private userDataService: UserDataService
  ) {
  }
  //
  // test() {
  //   return this.http.get('https://dog.ceo/api/breeds/image/random').subscribe();
  // }
  login() {
    const login = 'user_1';
    const password = 'user_1';

    // console.log("ssss");
    // const httpOptions = {
    //   headers: new HttpHeaders({
    //     'Authorization': 'Basic ' + btoa('user_1:user_1')
    //   }),
    //   withCredentials: true
    // }
    // return this.http.get<any>('app/auth/login', httpOptions).subscribe();
    return this.http.post<string>('app/auth/login', {login, password})
      .subscribe((role) => {
        this.userDataService.saveRole(role);
        this.userDataService.saveToken(btoa(`${login}:${password}`));
      });
  }

  test() {
    return this.http.get('https://dog.ceo/api/breeds/image/random').subscribe();
  }
}
