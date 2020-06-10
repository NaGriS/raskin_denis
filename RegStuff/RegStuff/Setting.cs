using Microsoft.Win32;

namespace RegStuff
{
    public class Setting
    {
        public string KeyName { get; set; }
        public string KeySetting { get; set; }
        public string KeyValue { get; set; }
       // public RegistryKey RegistryKeyName { get; set; }

        public Setting()
        {
        }

        public Setting(string keyName, string keySetting)
        {
            KeyName = keyName;
            KeySetting = keySetting;
           // RegistryKeyName = registryKeyName;
        }
    }
}
