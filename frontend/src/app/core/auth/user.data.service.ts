import {inject, Inject, Injectable, PLATFORM_ID} from "@angular/core";
import {DOCUMENT, isPlatformBrowser} from "@angular/common";

@Injectable({ providedIn: "root" })
export class UserDataService {

  private readonly platformId = inject(PLATFORM_ID);

  constructor(@Inject(DOCUMENT) private document: Document) {
  }

  //@ts-ignore
  getToken(): string | null {
    console.log(this.platformId)
    if(isPlatformBrowser(this.platformId)){
      //@ts-ignore
      return this.document.defaultView?.localStorage?.getItem("token");
    }

  }

  saveToken(token: string): void {
    this.document?.defaultView?.localStorage?.setItem("token",  token);
  }

  saveRole(role: string): void {
    this.document?.defaultView?.localStorage?.setItem("role", role);
  }

  destroyToken(): void {
    this.document?.defaultView?.localStorage?.removeItem("token");
  }
}
