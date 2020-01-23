import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypStudiow } from 'app/shared/model/typ-studiow.model';

type EntityResponseType = HttpResponse<ITypStudiow>;
type EntityArrayResponseType = HttpResponse<ITypStudiow[]>;

@Injectable({ providedIn: 'root' })
export class TypStudiowService {
  public resourceUrl = SERVER_API_URL + 'api/typ-studiows';

  constructor(protected http: HttpClient) {}

  create(typStudiow: ITypStudiow): Observable<EntityResponseType> {
    return this.http.post<ITypStudiow>(this.resourceUrl, typStudiow, { observe: 'response' });
  }

  update(typStudiow: ITypStudiow): Observable<EntityResponseType> {
    return this.http.put<ITypStudiow>(this.resourceUrl, typStudiow, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypStudiow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypStudiow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
