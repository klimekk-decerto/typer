import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Tournament} from "../../model/tournament";
import {Observable} from "rxjs";
import {TournamentCreate} from "../../model/tournament-create";

@Injectable({providedIn: 'root'})
export class TournamentRestService {

  constructor(private httpClient: HttpClient) {
  }

  readTournaments(): Observable<Tournament[]> {
    return this.httpClient.get<Tournament[]>("app/api/fetch/competitions");
  }

  addTournament(tournament: TournamentCreate): Observable<Tournament> {
    return this.httpClient.post<Tournament>("app/api/competition/tournament", tournament);
  }

}
