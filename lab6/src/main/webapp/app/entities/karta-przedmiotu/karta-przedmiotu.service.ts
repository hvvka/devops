import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';

type EntityResponseType = HttpResponse<IKartaPrzedmiotu>;
type EntityArrayResponseType = HttpResponse<IKartaPrzedmiotu[]>;

@Injectable({ providedIn: 'root' })
export class KartaPrzedmiotuService {
  public resourceUrl = SERVER_API_URL + 'api/karta-przedmiotus';
  public resourceUrlDownload = SERVER_API_URL + 'api/karta-przedmiotu';

  constructor(protected http: HttpClient) {}

  create(kartaPrzedmiotu: IKartaPrzedmiotu): Observable<EntityResponseType> {
    return this.http.post<IKartaPrzedmiotu>(this.resourceUrl, kartaPrzedmiotu, { observe: 'response' });
  }

  update(kartaPrzedmiotu: IKartaPrzedmiotu): Observable<EntityResponseType> {
    return this.http.put<IKartaPrzedmiotu>(this.resourceUrl, kartaPrzedmiotu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKartaPrzedmiotu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKartaPrzedmiotu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  downloadPdf(id: number): Observable<Blob> {
    return this.http.get(`${this.resourceUrlDownload}/downloads/${id}`, { responseType: 'blob' });
  }
}
