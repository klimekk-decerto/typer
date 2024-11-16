import {Injectable} from "@angular/core";

@Injectable({ providedIn: "root" })
export class UserDataService {

  // private storage: InjectionToken<Storage>;

  constructor() {
    // if(typeof window === 'undefined') {
    //   window = new Window()
    // }
    // this.storage = new InjectionToken<Storage>('Local Storage');
  }

  getToken(): string {
    // console.log('window:    ', window)
    return '';
    // return Window.localStorage["token"];
  }
  //
  // saveToken(token: string): void {
  //   window.localStorage["token"] = token;
  // }
  //
  // saveRole(role: string): void {
  //   window.localStorage["role"] = role;
  // }
  //
  // destroyToken(): void {
  //   window.localStorage.removeItem("token");
  // }
}
