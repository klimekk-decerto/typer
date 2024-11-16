import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";
import {MatAnchor, MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {AuthService} from "./auth.service";
import {MainPageService} from "../../feature/appointments/pages/main/main.page.service";

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatCheckbox,
    MatCardContent,
    MatCard,
    MatButton,
    MatInput,
    MatAnchor
  ],
  providers: [AuthService],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit{

  constructor(private router: Router, private authService: AuthService) {

  }

  form = new FormGroup({
    uname: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  submit() {
    // @ts-ignore
    this.authService.login(this.form.controls.uname.value, this.form.controls.password.value).subscribe((role) => {
      if (role === 'ADMIN') {
        this.router.navigate(['admin-dashboard']);
      } else {
        this.router.navigate(['user-dashboard']);
      }
    })
  }

  ngOnInit(): void {

  }
}
