import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDyscyplina } from 'app/shared/model/dyscyplina.model';

type EntityResponseType = HttpResponse<IDyscyplina>;
type EntityArrayResponseType = HttpResponse<IDyscyplina[]>;

@Injectable({ providedIn: 'root' })
export class DyscyplinaService {
  public resourceUrl = SERVER_API_URL + 'api/dyscyplinas';

  constructor(protected http: HttpClient) {}

  create(dyscyplina: IDyscyplina): Observable<EntityResponseType> {
    return this.http.post<IDyscyplina>(this.resourceUrl, dyscyplina, { observe: 'response' });
  }

  update(dyscyplina: IDyscyplina): Observable<EntityResponseType> {
    return this.http.put<IDyscyplina>(this.resourceUrl, dyscyplina, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDyscyplina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDyscyplina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
