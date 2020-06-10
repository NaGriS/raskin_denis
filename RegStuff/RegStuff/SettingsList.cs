using Newtonsoft.Json;
using System.Collections.Generic;

namespace RegStuff
{
    public class SettingsList
    {
        [JsonProperty("ListOfSettings")]
        public List<Setting> ListOfSettings { get; set; }

        public SettingsList()
        {
        }

        public SettingsList(List<Setting> settingsList)
        {
            ListOfSettings = settingsList;
        }
    }
}
