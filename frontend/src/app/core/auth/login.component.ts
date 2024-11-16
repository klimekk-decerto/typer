import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, Validators} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";
import {MatAnchor, MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [
    FormsModule,
    MatFormField,
    MatLabel,
    MatCheckbox,
    MatCardContent,
    MatCard,
    MatButton,
    MatInput,
    MatAnchor
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  constructor(private router: Router) {
  }

  form = new FormGroup({
    uname: new FormControl('', [Validators.required, Validators.minLength(6)]),
    password: new FormControl('', [Validators.required]),
  });

  get f() {
    return this.form.controls;
  }

  submit() {
    // console.log(this.form.value);
    // this.router.navigate(['/']);
  }
}
