import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

type EntityResponseType = HttpResponse<IEfektMinisterialny>;
type EntityArrayResponseType = HttpResponse<IEfektMinisterialny[]>;

@Injectable({ providedIn: 'root' })
export class EfektMinisterialnyService {
  public resourceUrl = SERVER_API_URL + 'api/efekt-ministerialnies';

  constructor(protected http: HttpClient) {}

  create(efektMinisterialny: IEfektMinisterialny): Observable<EntityResponseType> {
    return this.http.post<IEfektMinisterialny>(this.resourceUrl, efektMinisterialny, { observe: 'response' });
  }

  update(efektMinisterialny: IEfektMinisterialny): Observable<EntityResponseType> {
    return this.http.put<IEfektMinisterialny>(this.resourceUrl, efektMinisterialny, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEfektMinisterialny>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEfektMinisterialny[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
