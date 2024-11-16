import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MatchCreateCommand} from "../../model/match";

@Injectable({providedIn: 'root'})
export class MatchRestService {

  constructor(private httpClient: HttpClient) {
  }

  addMatch(matchCreateCommand: { date: string | null; competitionId: number; secondTeamId: string | null; firstTeamId: string | null }): Observable<any> {
    return this.httpClient.put<any>("app/api/competition/league/assign-round", matchCreateCommand);
  }

}
