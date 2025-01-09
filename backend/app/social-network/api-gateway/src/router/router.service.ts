import { Injectable, Inject } from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { Request } from 'express';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class RouterService {
  private readonly serviceClients: Map<string, ClientProxy>;

  constructor(
    @Inject('AUTH_SERVICE') private authClient: ClientProxy,
    @Inject('USER_SERVICE') private userClient: ClientProxy,
  ) {
    this.serviceClients = new Map([
      ['AUTH_SERVICE', authClient],
      ['USER_SERVICE', userClient],
    ]);
  }

  async forward(serviceName: string, request: Request) {
    const client = this.serviceClients.get(serviceName);
    if (!client) {
      throw new Error(`Service ${serviceName} not found`);
    }

    const pattern = {
      cmd: 'handle_request',
      path: request.path,
      method: request.method,
    };

    const payload = {
      body: request.body,
      query: request.query,
      params: request.params,
      headers: this.filterHeaders(request.headers),
      user: request['user'],
    };

    return firstValueFrom(client.send(pattern, payload));
  }

  private filterHeaders(headers: any): any {
    // Filter out headers we don't want to forward
    const { host: _, connection: __, ...filteredHeaders } = headers;
    return filteredHeaders;
  }
}
