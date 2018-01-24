package org.mangosbot.sound;

import org.urish.openal.ALException;

public class ImmersiveClient {
    public static void main(String[] args) throws ALException {
        new ImmersiveClient().run();
    }

    private void run() throws ALException {
        MusicDataSource dataSource = new MusicDataSource();
        MusicPlayer player = new MusicPlayer();

        dataSource.setHost("localhost");
        dataSource.setPort(9999);

        int account = 206;
        String curState = null, curZone = null;
        while (true) {
            String data = dataSource.get(account);
            if (data != null && !data.isEmpty()) {
                String[] params = data.split("\\|");
                if (params.length != 2)
                {
                    System.err.println(data);
                    continue;
                }

                String state = params[0];
                String zone = params[1];

                if (!state.equals(curState) || !zone.equals(curZone)) {
                    System.out.println(String.format("State='%s', Zone='%s'", state, zone));
                    player.transition(state, zone);
                    curState = state;
                    curZone = zone;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
