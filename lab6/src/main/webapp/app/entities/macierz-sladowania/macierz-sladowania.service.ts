import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

type EntityResponseType = HttpResponse<IEfektKsztalcenia>;
type EntityArrayResponseType = HttpResponse<IEfektKsztalcenia[]>;

@Injectable({ providedIn: 'root' })
export class MacierzSladowaniaService {
  public resourceUrl = SERVER_API_URL + 'api/efekt-ksztalcenias';

  constructor(protected http: HttpClient) {}

  create(efektKsztalcenia: IEfektKsztalcenia): Observable<EntityResponseType> {
    return this.http.post<IEfektKsztalcenia>(this.resourceUrl, efektKsztalcenia, { observe: 'response' });
  }

  update(efektKsztalcenia: IEfektKsztalcenia): Observable<EntityResponseType> {
    return this.http.put<IEfektKsztalcenia>(this.resourceUrl, efektKsztalcenia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEfektKsztalcenia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEfektKsztalcenia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
