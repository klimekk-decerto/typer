export interface Tournament {
  id: number,
  name: string;
}

export interface TournamentFull {
  id: number,
  teams: TournamentTeams[];
}


export interface TournamentTeams {
  id: number;
  name: string;
  groupName: string;
}
