import ws from "./main";
import { sendMessages } from "./messagesReducer";

export default () => {
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
