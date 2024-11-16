import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserDataService} from "./user.data.service";
import {Observable, tap} from "rxjs";

@Injectable()
export class AuthService {

  constructor(
    private http: HttpClient,
    private userDataService: UserDataService
  ) {
  }

  login(login: string, password: string): Observable<string> {
    return this.http.post<string>('app/auth/login', {login, password})
      .pipe(tap((role) => {
        this.userDataService.saveRole(role);
        this.userDataService.saveToken(btoa(`${login}:${password}`));
      }) );
  }
}
