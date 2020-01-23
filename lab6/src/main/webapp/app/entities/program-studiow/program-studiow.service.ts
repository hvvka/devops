import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProgramStudiow } from 'app/shared/model/program-studiow.model';

type EntityResponseType = HttpResponse<IProgramStudiow>;
type EntityArrayResponseType = HttpResponse<IProgramStudiow[]>;

@Injectable({ providedIn: 'root' })
export class ProgramStudiowService {
  public resourceUrl = SERVER_API_URL + 'api/program-studiows';

  constructor(protected http: HttpClient) {}

  create(programStudiow: IProgramStudiow): Observable<EntityResponseType> {
    return this.http.post<IProgramStudiow>(this.resourceUrl, programStudiow, { observe: 'response' });
  }

  update(programStudiow: IProgramStudiow): Observable<EntityResponseType> {
    return this.http.put<IProgramStudiow>(this.resourceUrl, programStudiow, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProgramStudiow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProgramStudiow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
