export interface Tournament {
  id: number,
  name: string;
}

interface TournamentTeamMatch {
  date: string;
  firstTeamId: number;
  firstTeamScore: number;
  matchId: number;
  secondTeamId: number;
}

export interface TournamentFull {
  id: number,
  matches: TournamentTeamMatch[],
  teams: TournamentTeams[];
}


export interface TournamentTeams {
  id: number;
  name: string;
  groupName: string;
}
