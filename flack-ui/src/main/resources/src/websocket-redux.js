import { sendMessages } from "./redux";

/**
 * How messages actually get sent down the websocket - Subscribes to Redux, and
 * sends any messages that get queued up.
 */
export default ws => {
  return store => {
    const wsConfig = store.getState().websocket.config;
    const send = ws(wsConfig);

    store.subscribe(() => {
      const sentIds = store
        .getState()
        .websocket.messages.pending.map(msg => {
          send(msg.body);
          return msg.id;
        })
        .reduce((sent, id) => sent.concat(id), []);

      if (sentIds.length > 0) store.dispatch(sendMessages(sentIds));
    });
  };
};
