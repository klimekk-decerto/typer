import {Component} from '@angular/core';
import {MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatAnchor, MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {NgForOf, NgIf} from "@angular/common";
import {TournamentRestService} from "../rest/tournament.rest.service";
import {TournamentCreate} from "../../model/tournament-create";

@Component({
  selector: 'app-form-dialog',
  templateUrl: 'form-dialog.component.html',
  standalone: true,
  imports: [
    MatDialogContent,
    MatFormField,
    FormsModule,
    MatButton,
    MatInput,
    MatLabel,
    MatDialogTitle,
    MatAnchor,
    MatCard,
    MatCardContent,
    ReactiveFormsModule,
    MatRadioButton,
    MatRadioGroup,
    NgIf,
    NgForOf
  ],
  styleUrls: ['form-dialog.component.scss']
})
export class FormDialogComponent {

  public formData: FormGroup;

  constructor(public dialogRef: MatDialogRef<FormDialogComponent>, private fb: FormBuilder,
              private tournamentRestService: TournamentRestService) {
    this.formData = this.fb.group({
      competitionName: new FormControl(''),
      teams: new FormControl(''),
      groups: this.fb.array([]),
    });
  }

  get groups(): FormArray {
    return this.formData.get('groups') as FormArray;
  }

  addField(): void {
    const fieldGroup = this.fb.group({
      group: '',
      teams: ''
    });
    this.groups.push(fieldGroup);
  }

  onSubmit(): void {
    // @ts-ignore
    const request = {
      tournamentName: this.formData.controls['competitionName'].value,
      groupTeams: this.map(this.groups.value)
    } as TournamentCreate;
    this.tournamentRestService.addTournament(request)
      .subscribe(tournament => this.dialogRef.close());
  }

  map(groups: { group: string, teams: string }[]) {
    const resultMap = groups.reduce((acc, item) => {
      // Podziel wartość `val` na tablicę
      const valuesArray = item.teams.split(',');
      // Dodaj do akumulatora z kluczem `name` i wartością jako tablica
      acc[item.group] = valuesArray;
      return acc;
    }, {} as Record<string, string[]>);
    return resultMap;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
