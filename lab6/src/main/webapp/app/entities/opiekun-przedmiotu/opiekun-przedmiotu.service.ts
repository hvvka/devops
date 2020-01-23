import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';

type EntityResponseType = HttpResponse<IOpiekunPrzedmiotu>;
type EntityArrayResponseType = HttpResponse<IOpiekunPrzedmiotu[]>;

@Injectable({ providedIn: 'root' })
export class OpiekunPrzedmiotuService {
  public resourceUrl = SERVER_API_URL + 'api/opiekun-przedmiotus';

  constructor(protected http: HttpClient) {}

  create(opiekunPrzedmiotu: IOpiekunPrzedmiotu): Observable<EntityResponseType> {
    return this.http.post<IOpiekunPrzedmiotu>(this.resourceUrl, opiekunPrzedmiotu, { observe: 'response' });
  }

  update(opiekunPrzedmiotu: IOpiekunPrzedmiotu): Observable<EntityResponseType> {
    return this.http.put<IOpiekunPrzedmiotu>(this.resourceUrl, opiekunPrzedmiotu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpiekunPrzedmiotu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpiekunPrzedmiotu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
