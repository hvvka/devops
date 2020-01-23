import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IZajecie } from 'app/shared/model/zajecie.model';

type EntityResponseType = HttpResponse<IZajecie>;
type EntityArrayResponseType = HttpResponse<IZajecie[]>;

@Injectable({ providedIn: 'root' })
export class ZajecieService {
  public resourceUrl = SERVER_API_URL + 'api/zajecies';

  constructor(protected http: HttpClient) {}

  create(zajecie: IZajecie): Observable<EntityResponseType> {
    return this.http.post<IZajecie>(this.resourceUrl, zajecie, { observe: 'response' });
  }

  update(zajecie: IZajecie): Observable<EntityResponseType> {
    return this.http.put<IZajecie>(this.resourceUrl, zajecie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZajecie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZajecie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
