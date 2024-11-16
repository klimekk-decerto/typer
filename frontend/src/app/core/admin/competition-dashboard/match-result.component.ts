import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {MatchesModel} from "../../model/competition-model";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

export type Model = {
  scoreA: number;
  scoreB: number;
}

@Component({
  selector: 'app-match-result',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatFormField,
    MatInput,
    MatButton,

  ],
  styleUrls: ['competition-dashboard.component.scss'],
  templateUrl: 'match-result-component.html'
})
export class MatchResultComponent implements OnInit {
  @Input() matchId: number;
  public formGroup: FormGroup;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder,
              private httpClient: HttpClient) {

    this.formGroup = this.fb.group({
      scoreA: new FormControl(''),
      scoreB: new FormControl('')
    });
    this.matchId = 0;
  }

  ngOnInit(): void {
  }


  predicate() {
    console.log(this.formGroup.controls['scoreA'].value);
    console.log(this.formGroup.controls['scoreB'].value);
  }

}
